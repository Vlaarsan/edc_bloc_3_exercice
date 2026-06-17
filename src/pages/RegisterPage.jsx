import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

export default function RegisterPage() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const register = async () => {
    await api.post("/users", {
      username,
      email,
      password,
    });

    navigate("/");
  };

  return (
    <div>
      <h1>Register</h1>

      <input placeholder="username" onChange={(e) => setUsername(e.target.value)} />
      <input placeholder="email" onChange={(e) => setEmail(e.target.value)} />
      <input placeholder="password" type="password" onChange={(e) => setPassword(e.target.value)} />

      <button onClick={register}>Créer compte</button>
    </div>
  );
}