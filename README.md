## Vídeo de apresentação
(Link do Vídeo Aqui)


## Integrantes
rm94265 | Daniel Ferreira dos Santos - 2TDST <br/>
rm94269 | Douglas Welber - 2TDSS <br/>
rm88383 | Felipe Jardim - 2TDST<br/>
rm94717 | Tarcisio Ferreira Couto - 2TDST<br/>


# SUP! Endpoints

- Usuário
  - [Registrar](#registrando-um-novo-usuario)
  - [Encontrar Todos](#encontrar-todos-usuarios)
  - [Detalhes](#detalhes-do-usuario)
  - [Atualizar](#atualizar-usuario)
  - [Deletar](#apagar-usuario)
  
- Empresa
  - [Registrar](#registrar-nova-empresa)
  - [Encontrar Todos](#encontrar-todas-empresas)
  - [Detalhes](#detalhes-da-empresa)
  - [Atualizar](#atualizar-empresa)
  - [Deletar](#deletar-empresa)


# Usuários
## Registrando um novo usuário

`POST` - sup-rm88383.azurewebsites.net/api/usuarios/registrar

**Campos da requisição**

| campo | tipo   | obrigatório | descrição                 |
| ----- | ------ | ----------- | ------------------------- |
| nome  | string | sim         | Nome completo do usuário. |
| email | string | sim         | E-mail do usuário.        |
| senha | string | sim         | Senha do usuário.         |

**Exemplos corpo da requisição**

```js
{
    nome: "Douglas Welber",
    email: 'usuario@gmail.com',
    senha: "123456789"
}
```

```js
{
  "nome": "Felipe Jardim",
  "email": "felipejardim@gmail.com",
  "senha": "123456"
}
```

```js
{
    nome: "Manuel Gomes",
    email: 'usuario@gmail.com',
    senha: "123456789"
}
```

**Respostas**

| código | descrição                       |
| ------ | ------------------------------- |
| 201    | Usuário registrado com sucesso. |
| 401    | Campos inválidos.               |

---

## Encontrando todos usuários

`GET` sup-rm88383.azurewebsites.net/api/usuarios

**Exemplo corpo da requisição (retorno)**

```js
[
    {
    id: 1
    nome: "Douglas Welber",
    email: 'usuario@gmail.com',
    senha: "123456789"
    },
    {
    "id": 31,
    "nome": "Felipe Jardim",
    "email": "felipejardim@gmail.com",
    "senha": "123456"
    },
    {
    id: 00256
    nome: "Manuel Gomes",
    email: 'usuario@gmail.com',
    senha: "123456789"
    }
]
```

---

## Detalhes do usuário

`GET` - sup-rm88383.azurewebsites.net/api/usuarios/{id}

**Exemplo corpo da requisição (retorno)**

```js
{
  "nome": "Felipe Jardim Aguiar Santos",
  "email": "felipejardim@gmail.com",
  "senha": "123456789"
}
```

**Respostas**

| código | descrição                                   |
| ------ | ------------------------------------------- |
| 201    | Detalhes do usuário resgatados com sucesso. |
| 401    | Campos inválidos.                           |

---

## Atualizando usuário

`PUT` - sup-rm88383.azurewebsites.net/api/usuarios/{id}

**Exemplo corpo da requisição**

```js
{
  "nome": "Felipe Jardim Aguiar Santos",
  "email": "felipejardim@gmail.com",
  "senha": "123456789"
}
```

| código | descrição                       |
| ------ | ------------------------------- |
| 201    | Usuário atualizado com sucesso. |
| 401    | Campos inválidos.               |

---

## Apagando usuário

`DELETE` sup-rm88383.azurewebsites.net/api/usuarios/{id}

**Respostas**

| código | descrição                    |
| ------ | ---------------------------- |
| 201    | Usuário apagado com sucesso. |
| 401    | Campos inválidos.            |

---

# Empresa
## Registrando nova empresa com ID do usuário

`POST` - sup-rm88383.azurewebsites.net/api/usuarios/addCompany/1

**Campos da requisição**

| campo   | tipo    | obrigatório | descrição                             |
| ------- | ------- | ----------- | ------------------------------------- |
| usuario | decimal | sim         | Id do usuário para o link da empresa. |
| email   | string  | sim         | E-mail corporativo do usuário.        |
| nome    | string  | sim         | Nome da empresa.                      |
| cargo   | string  | sim         | Cargo que o usuário ocupa na empresa. |

**Exemplo corpo da requisição**

```js
{
    usuario: 31,
    nome: "Apple",
    email: "Apple@apple.com",
    cargo: "Gerente",
}
```

```js
{
    usuario: 2,
    nome: "Coca Cola",
    email: "CocaCola@contato.com",
    cargo: "Vendedor",
}
```

```js
{
    usuario: 3,
    nome: "Amazon",
    email: "Amazon@amazoncontato.com",
    cargo: "CTO",
}
```

**Respostas**

| código | descrição                       |
| ------ | ------------------------------- |
| 201    | Empresa registrada com sucesso. |
| 401    | Campos inválidos.               |

---

## Encontre todas empresas

`GET` sup-rm88383.azurewebsites.net/api/empresas

**Exemplo corpo da requisição (retorno)**

```js
[
  {
    usuario: 31,
    nome: "Apple",
    email: "Apple@apple.com",
    cargo: "Gerente",
  },
  {
    usuario: 2,
    nome: "Coca Cola",
    email: "CocaCola@contato.com",
    cargo: "Vendedor",
  },
  {
    usuario: 3,
    nome: "Amazon",
    email: "Amazon@amazoncontato.com",
    cargo: "CTO",
  },
];
```

---

## Detalhes da empresa

`GET` - sup-rm88383.azurewebsites.net/api/empresas/{id}

**Exemplo corpo da requisição (retorno)**

```js
    {
        id: 1
        nome: "Amazon Brasil",
        email: "Amazon@amazoncontato.com",
        cargo: "CTO"
    }
```

**Respostas**

| código | descrição                                   |
| ------ | ------------------------------------------- |
| 201    | Detalhes da empresa resgatados com sucesso. |
| 401    | Campos inválidos.                           |

---

## Atualizando Empresa

`PUT` - sup-rm88383.azurewebsites.net/api/empresas/{id}

**Exemplo corpo da requisição**

```js
    {
        id: 31
        nome: "Amazon Brasil",
        email: "Amazon@amazoncontato.com",
        cargo: "CTO"
    }
```

| código | descrição                    |
| ------ | ---------------------------- |
| 201    | Empresa editada com sucesso. |
| 401    | Campos inválidos.            |

---

## Apagando empresa

`DELETE` sup-rm88383.azurewebsites.net/api/empresas/{id}

**Respostas**

| código | descrição                     |
| ------ | ----------------------------- |
| 201    | Empresa deletada com sucesso. |
| 401    | Campos inválidos              |

---
