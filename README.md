# Dairy Backend in Java Spring Boot
Need gradle to build and run the server.

### Code Limitations
- The inventory order has to be mozzarella, paneer, kanchan : constrained by fulfill methods in InventoryManager

### To do
- Add timestamp on orderRequest
- Return a item specific reason why the order request was not fulfilled
- INVESTIGATE: Predict the amount of products to be made in the future and get the order request fulfillment around it
- Payment system
- More robust checkout and ordering pages
- 'pending' order requests can clutter the databases and they can be accidents or abandons
- Cookie or signed user retention
- What happens when the product list increases by a lot? Adding all the products in the orderRequest class (and the inventory manager) cannot possibily be sustainabale

We need to also keep a manual track of the inventory. And keep the parity of this inventory management system to the cold store.