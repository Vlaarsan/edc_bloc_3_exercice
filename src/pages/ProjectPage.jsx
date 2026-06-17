import { useEffect, useState } from "react";
import api from "../api/axios";

export default function ProjectsPage() {
  const [projects, setProjects] = useState([]);

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    const res = await api.get("/projects");
    setProjects(res.data);
  };

  const createProject = async () => {
    await api.post("/projects", {
      name,
      description,
      startDate: new Date(),
      owner: { id: 1 },
    });

    load();
  };

  return (
    <div>
      <h1>Projets</h1>

      <input placeholder="nom" onChange={(e) => setName(e.target.value)} />
      <input placeholder="description" onChange={(e) => setDescription(e.target.value)} />

      <button onClick={createProject}>Créer</button>

      <ul>
        {projects.map((p) => (
          <li key={p.id}>{p.name}</li>
        ))}
      </ul>
    </div>
  );
}