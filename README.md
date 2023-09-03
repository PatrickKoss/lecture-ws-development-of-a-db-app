# Introduction

This codebase serves as a comprehensive repository, furnishing exemplary source code across diverse programming
paradigms aimed at elucidating the methodology for interfacing applications with relational database management
systems (RDBMS), including but not limited to SQLite. It transcends foundational techniques to delve into
Object-Relational Mapping (ORM) strategies for database abstraction and elucidates the utilization of migration
frameworks for effective database schema transitions. Additionally, it encapsulates the implementation of a
Representational State Transfer (REST) Application Programming Interface (API).

# Project Structure

Within the root directory, linguistic categories are partitioned into subdirectories to facilitate the selection of
a programming language that aligns with the user's proficiency and/or project requirements. Each language-specific
directory houses the following subdivisions:

- [cursor-simple](golang/cursor-simple): This segment elucidates the concept of leveraging a database-specific driver
  that instantiates a cursor for interacting with the database. It underscores the imperative nature of migration
  frameworks for effectively managing database schema alterations.
- [repository-simple](golang/repository-simple): Serving as an instructional guide, this directory showcases the
  abstraction capabilities of an ORM over rudimentary cursor-based interactions. It serves to demystify how frameworks
  such as Hibernate or Django models can streamline the intricacies of direct database interactions.
- [rest-simple](golang/rest-simple) This module furnishes a comprehensive guide to constructing a fully-fledged
  REST API. It outlines a multi-tiered architectural approach comprising a data persistence layer (repository),
  a business logic layer (service), and an exposure layer (infrastructure). Complementing this architecture, the module
  integrates a test suite to validate functionality and employs Swagger/OpenAPI for meticulous API documentation,
  facilitating client-side interactions.
- [rest-simple-exercise](golang/rest-simple-exercise) Designed as a hands-on exercise, this subdivision supplies a
  skeletal framework for crafting a REST API. Detailed directives for its completion can be located within the
  accompanying [README.md](golang/rest-simple-exercise/README.md) document.
