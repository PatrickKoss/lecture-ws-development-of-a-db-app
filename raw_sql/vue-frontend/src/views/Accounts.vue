<template>
  <v-layout id="root">
    <v-flex>
      <v-card :dark="state.dark">
        <v-card-title>
          <h2>Accounts</h2>
          <v-spacer></v-spacer>
          <v-text-field
                  append-icon="search"
                  hide-details
                  label="Search"
                  single-line
                  style="margin-right: 15px"
                  v-model="searchAccounts"
          />
          <v-spacer></v-spacer>
          <div>
            <v-layout row wrap>
              <v-btn @click="openAddDialog()" class="ma-2" icon large tile>
                <v-icon>add</v-icon>
              </v-btn>
            </v-layout>
          </div>
        </v-card-title>
        <v-data-table
                :headers="headers"
                :items="accounts"
                :loading="loading"
                :search="searchAccounts"
        >
          <v-progress-linear
                  color="blue"
                  indeterminate
                  slot="progress"
          />
          <template slot="items" slot-scope="props">
            <td class="text-xs-left tableData">{{ props.item.username }}</td>
            <td class="text-xs-left tableData">{{ props.item.email }}</td>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon
                    @click="editItem(item)"
                    class="mr-2"
                    small
            >
              mdi-pencil
            </v-icon>
            <v-icon
                    @click="deleteItem(item)"
                    small
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </v-card>
      <AccountDeleteDialog
              :account="account"
              v-model="deleteDialog"
              v-on:reload-accounts="reloadAccounts"
      />
      <AccountAddDialog
              :account="account"
              :edit="false"
              v-model="openDialog"
              v-on:reload-accounts="reloadAccounts"
      />
      <AccountAddDialog
              :account="account"
              :edit="true"
              v-model="editDialog"
              v-on:reload-accounts="reloadAccounts"
      />
    </v-flex>
  </v-layout>
</template>

<script>
  import {Component, Vue} from 'vue-property-decorator';
  import {AccountsRestClient} from "../model/accounts";
  import AccountDeleteDialog from "../components/Accounts/AccountDeleteDialog";
  import AccountAddDialog from "../components/Accounts/AccountAddDialog";

  @Component({
    components: {
      AccountDeleteDialog,
      AccountAddDialog,
    }
  })
  class Accounts extends Vue {
    accounts = [];
    state = this.$store.state;
    loading = false;
    searchAccounts = "";
    deleteDialog = false;
    openDialog = false;
    editDialog = false;
    account = {username: "", email: ""};

    headers = [
      {
        text: "Username",
        align: "left",
        value: "username",
        sortable: true
      },
      {text: "Email", value: "email", sortable: true},
      {text: "Actions", value: 'actions', sortable: false}
    ];

    async created() {
      this.reloadAccounts();
    }

    deleteItem(item) {
      this.deleteDialog = true;
      this.account = item;
    }

    editItem(item) {
      this.openDialog = false;
      this.editDialog = true;
      this.account = item;
    }

    openAddDialog() {
      this.account = {
        username: "",
        email: "",
        password: "",
      };
      this.openDialog = true;
      this.editDialog = false;
    }

    async reloadAccounts() {
      this.openDialog = false;
      this.editDialog = false;
      this.deleteDialog = false;
      this.loading = true;
      let accountsRestClient = new AccountsRestClient();
      this.accounts = await accountsRestClient.getAccounts();
      this.accounts = this.accounts.accounts;
      this.loading = false;
    }
  }

  export default Accounts
</script>
<style scoped>
  #root {
    height: 100%;
  }

  .tableData {
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }
</style>
