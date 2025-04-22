// src/components/ContaPagarForm.js
import React, { useState } from "react";
import axios from "axios";

function ContaPagarForm() {
  const [form, setForm] = useState({
    idFinanceiro: "",
    dataEmissao: "",
    dataVencimento: "",
    valorTotal: "",
    status: "",
    cnpj: ""
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const contaData = {
      financeiro: { idFinanceiro: parseInt(form.idFinanceiro) },
      dataEmissao: form.dataEmissao,
      dataVencimento: form.dataVencimento,
      valorTotal: parseFloat(form.valorTotal),
      status: form.status,
      fornecedor: { cnpj: form.cnpj }
    };

    try {
      await axios.post("http://localhost:8081/api/contas-pagar", contaData);
      alert("Conta a pagar criada com sucesso!");
      setForm({
        idFinanceiro: "",
        dataEmissao: "",
        dataVencimento: "",
        valorTotal: "",
        status: "",
        cnpj: ""
      });
    } catch (error) {
      console.error("Erro ao criar conta:", error);
      alert("Erro ao criar a conta!");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px" }}>
      <input type="number" name="idFinanceiro" placeholder="ID do Financeiro" value={form.idFinanceiro} onChange={handleChange} required />
      <input type="date" name="dataEmissao" placeholder="Data de Emissão" value={form.dataEmissao} onChange={handleChange} required />
      <input type="date" name="dataVencimento" placeholder="Data de Vencimento" value={form.dataVencimento} onChange={handleChange} required />
      <input type="number" name="valorTotal" placeholder="Valor Total" value={form.valorTotal} onChange={handleChange} required />
      <input type="text" name="status" placeholder="Status (ex: PENDENTE, PAGO)" value={form.status} onChange={handleChange} required />
      <input type="text" name="cnpj" placeholder="CNPJ do Fornecedor" value={form.cnpj} onChange={handleChange} required />
      <button type="submit">Cadastrar Conta</button>
    </form>
  );
}

export default ContaPagarForm;
