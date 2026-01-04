# Dairy Backend in Java Spring Boot
Need gradle to build and run the server.

### Setting up the application
This application uses postgres as the database and the uri of the database is set to be `localhost:5432/dairy`, so postgres has to be running in the local machine with a database named dairy.\
- [Download postgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) if not present and make sure the port is 5432.
- Install and create a database named dairy (unless the username is dairy, in which case the database `dairy` is already created).
    - Use `psql` terminal to connect to postgres server.
    - Login using the username and password from the installation.
    - `create database dairy;` in `psql` to create the database.
- Change the database to dairy `\c dairy`.
- Then copy the sql queries from `src/test/java/com/alpine/dairy/test.sql` and run them in `psql`.

Once the database is ready, create a `.env` file in the root directory with
```
DB_USER=<postgres username>
DB_PASSWORD=<postgres password>
```

### Running the server
On terminal, use `./gradlew bootrun` to start the server.

### Code Limitations
- The inventory order has to be mozzarella, paneer, kanchan : constrained by fulfill methods in InventoryManager

### To do
- Add timestamp on orderRequest and inventoryItem
- Change the name on database columns and entity alias from camel case (ex: updated_at instead of updatedAt)
- Return a item specific reason why the order request was not fulfilled
- Response Entity return on controllers
- INVESTIGATE: Predict the amount of products to be made in the future and get the order request fulfillment around it
- Payment system
- More robust checkout and ordering pages
- 'pending' order requests can clutter the databases and they can be accidents or abandons
- Cookie or signed user retention
- What happens when the product list increases by a lot? Adding all the products in the orderRequest class (and the inventory manager) cannot possibily be sustainabale

We need to also keep a manual track of the inventory. And keep the parity of this inventory management system to the cold store.