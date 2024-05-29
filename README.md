# 🌟 Simplified Tweet  🚀

Este é um sistema simplificado de tweet desenvolvido com as seguintes tecnologias:

- **Java 21** ☕
- **Spring Boot** 🌱
- **Spring Security 6** 🔒
- **OAuth2 Resource Server** 🔑
- **Docker 🐳**

## Funcionalidades Principais ✨

### Autenticação e Autorização 🔐

Implementação de login usando JWT token com um sistema de cargos (roles):


- **POST `/users`**: Cria um novo usuário
- **POST `/login`**: Realiza o login e retorna um JWT token

### Gerenciamento de Usuários 👥

- **GET `/users`**: Obtém todos os usuários (apenas para administradores)

### Gerenciamento de Tweets 🐦

- **GET `/tweets/feed`**: Obtém o feed de tweets com paginação
- **POST `/tweets`**: Cria um novo tweet
- **DELETE `/tweets/{id}`**: Deleta um tweet próprio ou qualquer tweet (se for administrador)

<hr>

#### Feito com 💙 por [Jaoow](https://github.com/Jaoow)
