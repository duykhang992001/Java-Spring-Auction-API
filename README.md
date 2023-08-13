# This is backend project for an auction application. Requirement link: https://hackmd.io/@nndkhoa9/BJKMTpH5r

## How to use this project:
- Clone the project via github
- Open project in IDE (recommend IntelliJ)
- Open MySQL server and run the sql script in the project
- Open file application.properties and edit database config which is suitable for your server.
- Build the project and run
- Open Swagger UI to test our API.

## Current progress: 
- 1.1: GET /api/v1/categories
- 1.2: GET /api/v1/products?page=0&size=5&order_by=desc&sort_by={endTimestamp, currentPrice, numOfBid}
- 1.3: GET /api/v1/categories/{categoryId}/products?page={page}&size={size}&lte={lte}&gte={gte}
- 1.4: GET /api/v1/products?page={page}&size={size}&q={search}&sort_by={endTimestamp, currentPrice}&order_by={asc,desc}
- 1.5: GET /api/v1/products/{productId} and GET /api/v1/categories/{categoryId}/products?page=0&size=5&exclusiveProductId={productId}
- 1.6: POST /api/v1/auth/registration with request body { name: "", address: "", email: "", password: "" }, PUT /api/v1/auth/registration/otp/verification with request body { user_id: "", otp_code: "" }
- 2.1: POST /api/v1/users/{userId}/favorite and DELETE /api/v1/users/{userId}/favorite with request body { product_id: "" }
- 2.2: POST /api/v1/products/{productId}/auction with request body { user_id: "", price: }
- 2.3: GET /api/v1/products/{productId}/histories?page={page}&size={size}&order_by={asc,desc}
- 2.4: PUT /api/v1/users/{userId}/profile with request body { email: "", name: "", address: "" }, GET /api/v1/users/{userId}/points + GET /api/v1/users/{userId}/reviews?page={page}&size={size}, GET /api/v1/users/{userId}/favorite?page={page}&size={size}&lte={lte}&gte={gte}, GET /api/v1/users/{userId}/auctioning?page={page}&size={size}, GET /api/v1/users/{userId}/won?page={page}&size={size}, POST /api/v1/products/{productId}/owner/reviews with request body: { sender_id: "", comment: "", is_liked: "" }, PUT /api/v1/users/{userId}/password with request body { new_password: "", old_password: "" }
- 2.5: POST /api/v1/users/{userId}/roles/requests/upgrade
- 3.1: POST /api/v1/products with request body { name: "", current_price: , additional_price: , buy_now_price: , category_id: "", owner_id: "", is_auto_extend_time: , start_timestamp: , end_timestamp: , description: "", images: [ { is_thumbnail_image: , url: "" } ] }
- 3.2: POST /api/v1/products/{productId}/descriptions with request body { content: "" }
- 3.3: PUT /api/v1/products/auction/decline/{auctionId} 
- 3.4: GET /api/v1/users/{userId}/active?page={page}&size={size}, GET /api/v1/users/{userId}/expired?page={page}&size={size}, POST /api/v1/products/{productId}/winner/reviews with request body: { sender_id: "", comment: "", is_liked: "" }
- 4.1: GET /api/v1/categories, GET /api/v1/categories/{categoryId}, POST /api/v1/categories, POST /api/v1/categories/{categoryId}, PUT /api/v1/categories/inner/{categoryId}, PUT /api/v1/categories/outer/{categoryId}, DELETE /api/v1/categories/inner/{categoryId}, DELETE /api/v1/categories/outer/{categoryId}
- 4.2: DELETE /api/v1/products/{productId}
- 4.3: GET /api/v1/users/roles/requests?page={page}&size={size}, PUT /api/v1/users/roles/requests/accept/{requestId}, POST /api/v1/users/{userId}/roles/requests/downgrade
- 5.2: PUT /api/v1/users/{userId}/profile with request body { email: "", name: "", address: "" }
- 5.3: PUT /api/v1/users/{userId}/password with request body { new_password: "", old_password: "" }
- 5.4: POST /api/v1/auth/password/otp with request body { email: "" }, POST /api/v1/auth/password/otp/verification with request body { user_id: "", otp_code: "" }, PUT /api/v1/auth/password with request body { token: "", user_id: "", password: "" }
