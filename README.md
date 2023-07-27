# VoterProjectWithRedis
In this Redis project, the following functionalities have been implemented:

1. **Redis Configuration (RedisConfig):**
   - Configuring the Redis connection using the LettuceConnectionFactory.
   - Setting up RedisTemplate with JSON serializer for values and String serializer for keys.

2. **Security Filter Configuration (SecurityFilterConfig):**
   - Configuring Spring Security to enable authentication for the endpoints.
   - Defining an authentication filter (jwtAuthenticationFilter) to handle JWT authentication.
   - Permitting registration without authentication ("/auth/rp1/**").
   - Authenticating other requests using JWT.

3. **Voter Application Configuration (VoterApplicationConfig):**
   - Configuring the UserDetailsService and AuthenticationProvider for authentication.
   - Defining a custom UserDetails implementation (Voter) with roles for Spring Security.
   - Setting up a PasswordEncoder (BCryptPasswordEncoder).

4. **Voter JWT Service (VoterJwtService):**
   - Generating and validating JWT tokens.
   - Extracting the username from the JWT token.
   - Storing and retrieving tokens from Redis using RedisTemplate.

5. **Voter Controller (VoterController):**
   - Implementing REST endpoints for user registration and login.
   - Securing the endpoints using Spring Security.

6. **Request and Response Models:**
   - VoterAuthenticationRequest and VoterAuthenticationResponse for authentication.
   - VoterRegisterRequest for user registration.

7. **Entity Classes:**
   - Voter entity representing user details, including roles.
   - Token entity representing JWT tokens with Redis storage.

8. **Repositories:**
   - RedisVoterRepo implementing VoterRepository for storing and retrieving Voter objects in Redis.
   - RedisTokenRepo implementing TokenRepository for storing and retrieving Token objects in Redis.

Overall, this project focuses on implementing user authentication and authorization using JWT tokens and Redis for token storage. It provides user registration, login, and endpoint security using Spring Security with Redis integration.
