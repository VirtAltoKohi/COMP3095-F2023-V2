print("START");

db = db.getSiblingDB("product-service");

db.createUser(
    {
        user: "mongoadmin",
        pwd: "password",
        roles: [{role: "readWrite", db: "product-service"}]
    }
)

db.createCollection("user");

print("END");
print("START");

db = db.getSibling("product-service");

db.createUser(
    {
        user: "mongoadmin",
        pwd: "password",
        roles: [{role: "readWrite", db: "product-service"}]
    }
)

db.createCollection("user");

print("END");