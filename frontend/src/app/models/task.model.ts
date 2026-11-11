export interface Task {
  id: number;
  title: string;
  description: string;
  priority: string;
  status: string;
  dueDate: string;
  endDate: string | null;
  projectId: number;
  assignedToId: number | null;
}
