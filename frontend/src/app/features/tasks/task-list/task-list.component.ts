import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../../core/services/task.service';
import { Task } from '../../../models/task.model';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  newTask: any = { title: '', description: '', priority: 'MEDIUM', status: 'TODO', projectId: 1 };

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.taskService.getAll().subscribe((t) => (this.tasks = t));
  }

  create() {
    this.taskService.create(this.newTask).subscribe({
      next: () => {
        this.load();
        this.newTask = { title: '', description: '', priority: 'MEDIUM', status: 'TODO', projectId: 1 };
      }
    });
  }
}
