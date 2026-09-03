## API Test Automation — GoRest

Rest Assured + TestNG framework testing the GoRest public API 
(https://gorest.co.in), covering user, post, and comment resources.

### Coverage
- CRUD operations (Create, Read, Update, Delete)
- Negative/validation testing (missing fields, invalid formats, duplicates)
- Authentication scenarios (missing/invalid tokens)
- Schema validation
- Cross-resource relationships (users → posts)
- Boundary value testing

### Structure
- `src/test/java/tests` — test classes by resource
- `src/test/java/pojo` — request/response POJOs
- `src/test/java/base` — shared request specs, auth token handling
- `src/test/resources/schemas` — JSON schema files for validation

### Running tests
`mvn test`
