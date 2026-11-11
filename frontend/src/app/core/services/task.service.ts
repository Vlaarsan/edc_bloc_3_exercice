import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Task } from '../../models/task.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TaskService {

  constructor(private api: ApiService) {}

  getAll(): Observable<Task[]> {
    return this.api.get<Task[]>('/tasks');
  }

  create(task: Omit<Task, 'id'>): Observable<Task> {
    return this.api.post<Task>('/tasks', task);
  }
}
