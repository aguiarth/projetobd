import React, { useState } from "react";
import axios from "axios";

function FinanceiroForm() {
  const [financeiro, setFinanceiro] = useState({
    historicoLucro: "",
    historicoPrejuizo: "",
  });

  const handleChange = (e) => {
    setFinanceiro({
      ...financeiro,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Garantir que os valores numéricos estejam corretos
    const financeiroData = {
      ...financeiro,
      historicoLucro: parseFloat(financeiro.historicoLucro),
      historicoPrejuizo: parseFloat(financeiro.historicoPrejuizo),
    };

    try {
      await axios.post("http://localhost:8081/api/financeiros", financeiroData, {
        headers: {
          "Content-Type": "application/json", // Garantir que o tipo de conteúdo é JSON
        },
      });
      alert("Financeiro inserido com sucesso!");
      setFinanceiro({
        historicoLucro: "",
        historicoPrejuizo: "",
      });
    } catch (error) {
      console.error("Erro ao inserir:", error);
      alert("Erro ao inserir os dados!");
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px" }}
    >
      <input
        type="number"
        name="historicoLucro"
        placeholder="Histórico de Lucro"
        value={financeiro.historicoLucro}
        onChange={handleChange}
      />
      <input
        type="number"
        name="historicoPrejuizo"
        placeholder="Histórico de Prejuízo"
        value={financeiro.historicoPrejuizo}
        onChange={handleChange}
      />
      <button type="submit">Cadastrar</button>
    </form>
  );
}

export default FinanceiroForm;
