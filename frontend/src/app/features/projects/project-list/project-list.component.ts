import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../../core/services/project.service';
import { Project } from '../../../models/project.model';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  newProject: any = { name: '', description: '', startDate: '', ownerId: 1 };

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.projectService.getAll().subscribe((p) => (this.projects = p));
  }

  create() {
    this.projectService.create(this.newProject).subscribe({
      next: () => {
        this.load();
        this.newProject = { name: '', description: '', startDate: '', ownerId: 1 };
      }
    });
  }
}
