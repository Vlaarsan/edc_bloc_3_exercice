import { useEffect, useState } from "react";
import api from "../api/axios";

export default function TasksPage() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    api.get("/tasks").then((res) => setTasks(res.data));
  }, []);

  return (
    <div>
      <h1>Tasks</h1>

      <ul>
        {tasks.map((t) => (
          <li key={t.id}>
            {t.title} - {t.status} - {t.priority}
          </li>
        ))}
      </ul>
    </div>
  );
}