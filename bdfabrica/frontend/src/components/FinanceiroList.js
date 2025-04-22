import React, { useState } from "react";
import axios from "axios";

function FinanceiroList() {
  const [idBusca, setIdBusca] = useState("");
  const [financeiro, setFinanceiro] = useState(null);

  const handleBuscar = async () => {
    try {
      const response = await axios.get(`http://localhost:8081/api/financeiros/${idBusca}`);
      setFinanceiro(response.data);
    } catch (error) {
      console.error("Erro ao buscar financeiro:", error);
      alert("Financeiro não encontrado!");
      setFinanceiro(null);
    }
  };

  return (
    <div>
      <h2>Buscar Financeiro por ID</h2>
      <input
        type="number"
        placeholder="Digite o ID"
        value={idBusca}
        onChange={(e) => setIdBusca(e.target.value)}
      />
      <button onClick={handleBuscar}>Buscar</button>

      {financeiro && (
        <div style={{ marginTop: "20px" }}>
          <p><strong>ID:</strong> {financeiro.idFinanceiro}</p>
          <p><strong>Lucro:</strong> {financeiro.historicoLucro}</p>
          <p><strong>Prejuízo:</strong> {financeiro.historicoPrejuizo}</p>
          <p><strong>Data de Atualização:</strong> {financeiro.dataAtualizacao}</p>
        </div>
      )}
    </div>
  );
}

export default FinanceiroList;
