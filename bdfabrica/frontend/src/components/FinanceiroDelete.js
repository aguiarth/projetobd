import React, { useState } from "react";
import axios from "axios";

function FinanceiroDelete() {
  const [id, setId] = useState("");

  const handleDelete = async () => {
    if (!id) {
      alert("Digite um ID válido.");
      return;
    }

    try {
      await axios.delete(`http://localhost:8081/api/financeiros/${id}`);
      alert(`Financeiro com ID ${id} deletado com sucesso!`);
      setId("");
    } catch (error) {
      console.error("Erro ao deletar financeiro:", error);
      alert("Erro ao deletar. Verifique se o ID existe.");
    }
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px" }}>
      <input
        type="number"
        placeholder="Digite o ID para deletar"
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <button onClick={handleDelete}>Deletar</button>
    </div>
  );
}

export default FinanceiroDelete;
