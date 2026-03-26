# Testes da API - Controle de Gastos

## Criar Categoria

POST /categorias

Request:
{
  "nome": "Alimentação"
}

Response:
200 OK

---

## Criar Usuário

POST /usuarios

Request:
{
  "nome": "Guilherme",
  "email": "gui@email.com",
  "senha": "123456"
}

---

## Criar Lançamento

POST /lancamentos

Request:
{
  "descricao": "Almoço",
  "valor": 25.50,
  "tipo": "DESPESA",
  "data": "2026-03-26",
  "usuario": { "id": 1 },
  "categoria": { "id": 1 }
}

---

## Listar Lançamentos

GET /lancamentos/usuario/1