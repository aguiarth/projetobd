import React, { useState } from "react";
import FinanceiroForm from "./components/FinanceiroForm";
import FinanceiroList from "./components/FinanceiroList";
import FinanceiroDelete from "./components/FinanceiroDelete";
import FinanceiroUpdate from "./components/FinanceiroUpdate";
import ContaPagarForm from "./components/ContaPagarForm";

function App() {
  const [mostrarOpcoesFinanceiro, setMostrarOpcoesFinanceiro] = useState(false);
  const [opcaoSelecionadaFinanceiro, setOpcaoSelecionadaFinanceiro] = useState("");

  const [mostrarOpcoesContaPagar, setMostrarOpcoesContaPagar] = useState(false);
  const [opcaoSelecionadaContaPagar, setOpcaoSelecionadaContaPagar] = useState("");


  return (
    <div style={{ padding: "20px" }}>
      <h1>Bem-vindo ao Sistema Financeiro</h1>

      <button onClick={() => setMostrarOpcoesFinanceiro(!mostrarOpcoesFinanceiro)}>
        Financeiro
      </button>

      <button onClick={() => setMostrarOpcoesContaPagar(!mostrarOpcoesContaPagar)}>
        ContaPagar
      </button>

      {mostrarOpcoesContaPagar && (
        <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
          <button onClick={() => setOpcaoSelecionadaContaPagar("inserir")}>Inserir</button>
        </div>
      )}

      <div style={{marginTop: "20px"}}>
        {opcaoSelecionadaContaPagar === "inserir" && <ContaPagarForm />}
      </div>

      {mostrarOpcoesFinanceiro && (
        <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
          <button onClick={() => setOpcaoSelecionadaFinanceiro("inserir")}>Inserir</button>
          <button onClick={() => setOpcaoSelecionadaFinanceiro("buscar")}>Buscar</button>
          <button onClick={() => setOpcaoSelecionadaFinanceiro("deletar")}>Deletar</button>
          <button onClick={() => setOpcaoSelecionadaFinanceiro("atualizar")}>Atualizar</button>
        </div>
      )}

    
      <div style={{ marginTop: "20px" }}>
        {opcaoSelecionadaFinanceiro === "inserir" && <FinanceiroForm />}
        {opcaoSelecionadaFinanceiro === "buscar" && <FinanceiroList />}
        {opcaoSelecionadaFinanceiro === "deletar" && <FinanceiroDelete />}
        {opcaoSelecionadaFinanceiro === "atualizar" && <FinanceiroUpdate />}
      </div>
    </div>
    
  );
}

export default App;
