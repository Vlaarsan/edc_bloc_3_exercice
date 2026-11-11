import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Project } from '../../models/project.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProjectService {

  constructor(private api: ApiService) {}

  getAll(): Observable<Project[]> {
    return this.api.get<Project[]>('/projects');
  }

  create(project: Omit<Project, 'id'>): Observable<Project> {
    return this.api.post<Project>('/projects', project);
  }
}
