import React, { useState } from "react";
import axios from "axios";

function FinanceiroUpdate() {
  const [id, setId] = useState("");
  const [financeiro, setFinanceiro] = useState(null);
  const [mensagem, setMensagem] = useState("");

  const buscarFinanceiro = async () => {
    try {
      const response = await axios.get(`http://localhost:8081/api/financeiros/${id}`);
      setFinanceiro(response.data);
      setMensagem("");
    } catch (error) {
      console.error("Erro ao buscar:", error);
      setMensagem("Financeiro não encontrado.");
      setFinanceiro(null);
    }
  };

  const handleChange = (e) => {
    setFinanceiro({
      ...financeiro,
      [e.target.name]: e.target.value,
    });
  };

  const atualizarFinanceiro = async (e) => {
    e.preventDefault();
    try {
      const atualizado = {
        ...financeiro,
        historicoLucro: parseFloat(financeiro.historicoLucro),
        historicoPrejuizo: parseFloat(financeiro.historicoPrejuizo),
      };
      await axios.put(`http://localhost:8081/api/financeiros/${id}`, atualizado);
      alert("Financeiro atualizado com sucesso!");
    } catch (error) {
      console.error("Erro ao atualizar:", error);
      alert("Erro ao atualizar os dados!");
    }
  };

  return (
    <div>
      <h2>Atualizar Financeiro</h2>
      <input
        type="number"
        placeholder="Digite o ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <button onClick={buscarFinanceiro}>Buscar</button>

      {mensagem && <p>{mensagem}</p>}

      {financeiro && (
        <form
          onSubmit={atualizarFinanceiro}
          style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px", marginTop: "20px" }}
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
          <button type="submit">Atualizar</button>
        </form>
      )}
    </div>
  );
}

export default FinanceiroUpdate;
