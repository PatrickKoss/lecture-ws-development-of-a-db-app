#!/usr/bin/env bash
set -euo pipefail

solution_root="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
sql_only=false
with_starters=false
while [[ "${1:-}" == --* ]]; do
    case "$1" in
        --sql-only) sql_only=true ;;
        --with-starters) with_starters=true ;;
        --help)
            printf 'Usage: bash verify.sh [--sql-only | --with-starters] [domain ...]\n'
            printf 'Without domain arguments, checks every exercise including _template.\n'
            exit 0 ;;
        *) printf 'Unknown option: %s\n' "$1" >&2; exit 1 ;;
    esac
    shift
done
if [[ "$sql_only" == true && "$with_starters" == true ]]; then
    printf 'Choose --sql-only or --with-starters, not both.\n' >&2
    exit 1
fi

command -v sqlite3 >/dev/null || { printf 'sqlite3 is required.\n' >&2; exit 1; }
if [[ "$sql_only" == false ]]; then
    command -v java >/dev/null || { printf 'Java 21 is required.\n' >&2; exit 1; }
fi

domains=("$@")
if [[ ${#domains[@]} -eq 0 ]]; then
    for exercise in "$solution_root"/../*/a0-domain; do
        domains+=("$(basename -- "$(dirname -- "$exercise")")")
    done
    domains+=("common-example")
fi

for domain in "${domains[@]}"; do
    exercise_root="$solution_root/../$domain"
    if [[ "$domain" == "common-example" ]]; then
        exercise_root="$solution_root/../../common-example/exercises"
    fi
    if [[ ! "$domain" =~ ^[a-z][a-z-]*$ && "$domain" != "_template" ]] ||
        [[ ! -d "$exercise_root/a0-domain" ]]; then
        printf 'Unknown exercise: %s\n' "$domain" >&2
        exit 1
    fi
    project="$solution_root/$domain"
    log_dir="$solution_root/build/verification/$domain"
    mkdir -p -- "$log_dir"
    for artifact in design/domain-notes.txt design/er.mmd design/er.svg \
        design/relations.sql design/normalization.md sql/query-plan.sql; do
        [[ -s "$project/$artifact" ]] || { printf 'Missing %s/%s\n' "$domain" "$artifact" >&2; exit 1; }
    done
    for file in schema.sql seed.sql queries.sql; do
        [[ -f "$project/sql/$file" ]] || { printf 'Missing %s/sql/%s\n' "$domain" "$file" >&2; exit 1; }
    done
    printf '%s: SQL schema, seed and queries\n' "$domain"
    if ! {
        printf 'PRAGMA foreign_keys=ON;\n'
        cat -- "$project/sql/schema.sql" "$project/sql/seed.sql" "$project/sql/queries.sql" "$project/sql/query-plan.sql"
    } | sqlite3 -bail :memory: >"$log_dir/sql.log" 2>&1; then
        cat -- "$log_dir/sql.log" >&2
        exit 1
    fi
    integrity="$(
        {
            printf 'PRAGMA foreign_keys=ON;\n'
            cat -- "$project/sql/schema.sql" "$project/sql/seed.sql"
            printf '\nPRAGMA foreign_key_check;\nPRAGMA integrity_check;\n'
        } | sqlite3 -bail :memory:
    )"
    if [[ "$integrity" != "ok" ]]; then
        printf '%s: database integrity check failed:\n%s\n' "$domain" "$integrity" >&2
        exit 1
    fi
    if [[ "$sql_only" == true ]]; then
        continue
    fi
    [[ -s "$project/api/openapi.json" ]] || { printf 'Missing %s/api/openapi.json\n' "$domain" >&2; exit 1; }
    for layer in jdbc repository spring; do
        [[ -f "$project/$layer/gradlew" ]] || { printf 'Missing %s/%s/gradlew\n' "$domain" "$layer" >&2; exit 1; }
        printf '%s: %s tests\n' "$domain" "$layer"
        if ! (cd -- "$project/$layer" && bash ./gradlew test --console=plain --max-workers=2) >"$log_dir/$layer.log" 2>&1; then
            cat -- "$log_dir/$layer.log" >&2
            exit 1
        fi
        if [[ "$layer" == spring ]] && ! cmp -s -- "$project/spring/build/openapi/openapi.json" "$project/api/openapi.json"; then
            printf '%s: OpenAPI export is missing or differs from api/openapi.json. Rerun Spring tests with --rerun-tasks and refresh the snapshot.\n' "$domain" >&2
            exit 1
        fi
    done
    if [[ "$with_starters" == true ]]; then
        printf '%s: participant ORM starter baseline\n' "$domain"
        if ! (cd -- "$exercise_root/c2-spring-resource/starter" && \
            bash ./gradlew test --console=plain --max-workers=2) >"$log_dir/starter.log" 2>&1; then
            cat -- "$log_dir/starter.log" >&2
            exit 1
        fi
    fi
done
printf 'Passed for %s exercise(s). Logs: %s/build/verification/\n' "${#domains[@]}" "$solution_root"
