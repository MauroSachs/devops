
# Título do Projeto

Uma breve descrição sobre o que esse projeto faz e para quem ele é

Endpoints:

## Documentação da API


### CRESCINEMA
Rede social de criticas e opiniões de filmes, onde cada usuario pode postar uma nota e uma opinião sobre um filme de sua escolha.





----------Usuarios-----------

#### Realiza login recebendo email e senha

```http
  POST /login
```
#### Realiza logout do usuario logado

```http
  POST /logout
```

#### Realiza cadastro de um usuário novo

```http
  POST /usuarios
```

#### Retorna pagina de usuarios

```http
  GET /usuarios
```

#### Retorna detalhes de um usuario

```http
  GET /usuarios/{id}
```

#### Retorna posts de um usuario

```http
  GET /usuarios/{id}/posts
```

#### Editar o nome, apelido e imagem de um Usuario

```http
  PUT /usuarios/{id}/editar
```
------------ AMIZADES -------
#### Retorna pagina de amigos do usuario logado

```http
  GET /usuarios/amigos/paginado
```
#### Retorna lista com todos amigos por id do usuario

```http
  GET /usuarios/amigos/{id}
```
#### Retorna usuarios que solicitaram amizade ao logado

```http
  GET /usuarios/solicitacoes
```
#### Solicita amizade de um usuario

```http
  PUT /usuarios/{id}/solicitar
```
#### Aceita amizade de um usuario

```http
  PUT /usuarios/{id}/aceitar
```
#### Recusa amizade de um usuario

```http
  PUT /usuarios/{id}/recusar
```
#### Remove um amigo

```http
  DELETE /usuarios/{id}/remover
```

-------------Posts ------------

#### Retorna pagina com posts permitidos

```http
  GET /posts
```
#### Retorna post por id

```http
  GET /posts/{id}
```
#### Cria post novo

```http
  POST /posts/
```
#### Comenta em um post

```http
  POST /posts/{id}/comentar
```

#### Curte um post

```http
  PUT /posts/{id}/curtir
```

#### Descurte um post

```http
  PUT /posts/{id}/descurtir
```
#### Altera privacidade de um post

```http
  PUT /posts/{id}
```

#### Deleta um post

```http
  DELETE /posts/{id}
```