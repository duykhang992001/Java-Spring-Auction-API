# This is backend project for an auction application. Requirement link: https://hackmd.io/@nndkhoa9/BJKMTpH5r

Current progress: 
- 1.1: GET /api/v1/categories
- 1.2: GET /api/v1/products?page=0&size=5&order_by=desc&sort_by={endTimestamp, currentPrice, numOfBid}
- 1.3: GET /api/v1/categories/{categoryId}/products?page={page}&size={size}
- 1.4: GET /api/v1/products?page={page}&size={size}&q={search}&sort_by={endTimestamp, currentPrice}&order_by={asc,desc}
- 1.5: GET /api/v1/products/{productId} and GET /api/v1/categories/{categoryId}/products?page=0&size=5&exclusiveProductId={productId}
- 2.1: POST /api/v1/users/{userId}/favorite and DELETE /api/v1/users/{userId}/favorite with request body { product_id: "" }
- 2.3: GET /api/v1/products/{productId}/histories?page={page}&size={size}&order_by={asc,desc}
- 2.5: POST /api/v1/users/{userId}/roles/histories