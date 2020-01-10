# users-api
App for registering users with login.

## Usage
 - After build, you'll need to insert the root user in the database to use the app (root.sql)
 - After that, you can sign in application with root user credentials:

*POST <host>/login*

*BODY:* 

```
{
  "email":"root@root",
  "password":"root"
}
```

RESPONSE: User info and JWT token to access "/user" endpoint and create new users

- To create new users you'll need to POST into "<host>/users" resource passing the previous received token in "Autorizathon"
header as a Bearer token:

```Authorization = Bearer <jwt_token>```

And the user to be created in the request body following the example structure: 

*BODY:*

```   
{
  "name": "João da Silva",
  "email": "joao@silva.org",
  "password": "hunter2",
  "phones": [
      {
          "number": "987654321",
          "ddd": "21"
      }
   ]
}
```

- After creation, you will can use created user's email and password to login and repeat the process
