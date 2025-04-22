import React, { useState } from "react";
import FinanceiroForm from "./components/FinanceiroForm";
import FinanceiroList from "./components/FinanceiroList";
import FinanceiroDelete from "./components/FinanceiroDelete";
import FinanceiroUpdate from "./components/FinanceiroUpdate";

function App() {
  const [mostrarOpcoes, setMostrarOpcoes] = useState(false);
  const [opcaoSelecionada, setOpcaoSelecionada] = useState("");

  return (
    <div style={{ padding: "20px" }}>
      <h1>Bem-vindo ao Sistema Financeiro</h1>

      <button onClick={() => setMostrarOpcoes(!mostrarOpcoes)}>
        Financeiro
      </button>

      {mostrarOpcoes && (
        <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
          <button onClick={() => setOpcaoSelecionada("inserir")}>Inserir</button>
          <button onClick={() => setOpcaoSelecionada("buscar")}>Buscar</button>
          <button onClick={() => setOpcaoSelecionada("deletar")}>Deletar</button>
          <button onClick={() => setOpcaoSelecionada("atualizar")}>Atualizar</button>
        </div>
      )}

      <div style={{ marginTop: "20px" }}>
        {opcaoSelecionada === "inserir" && <FinanceiroForm />}
        {opcaoSelecionada === "buscar" && <FinanceiroList />}
        {opcaoSelecionada === "deletar" && <FinanceiroDelete />}
        {opcaoSelecionada === "atualizar" && <FinanceiroUpdate />}
      </div>
    </div>
  );
}

export default App;
