import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const [projects, setProjects] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/projects").then((res) => setProjects(res.data));
  }, []);

  return (
    <div>
      <h1>Dashboard</h1>

      <button onClick={() => navigate("/projects")}>
        Voir projets
      </button>

      <ul>
        {projects.map((p) => (
          <li key={p.id}>
            {p.name} - {p.description}
          </li>
        ))}
      </ul>
    </div>
  );
}