import { TestBed } from '@angular/core/testing';
import { TaskService } from './task.service';
import { ApiService } from './api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TaskService', () => {
  let service: TaskService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TaskService, ApiService]
    });
    service = TestBed.inject(TaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll should return tasks', () => {
    const mockTasks = [
      { id: 1, title: 'Tâche A', description: 'Desc', priority: 'HIGH', status: 'TODO', dueDate: '2024-02-01', endDate: null, projectId: 1, assignedToId: null }
    ];
    service.getAll().subscribe((tasks) => {
      expect(tasks.length).toBe(1);
      expect(tasks[0].title).toBe('Tâche A');
    });
    const req = httpMock.expectOne('http://localhost:8080/tasks');
    expect(req.request.method).toBe('GET');
    req.flush(mockTasks);
  });

  it('create should post a task', () => {
    const newTask = { title: 'Tâche B', description: 'Desc', priority: 'LOW', status: 'TODO', projectId: 1 };
    const created = { id: 2, ...newTask, endDate: null, assignedToId: null, dueDate: null };
    service.create(newTask as any).subscribe((task) => {
      expect(task.id).toBe(2);
    });
    const req = httpMock.expectOne('http://localhost:8080/tasks');
    expect(req.request.method).toBe('POST');
    expect(req.request.body.title).toBe('Tâche B');
    req.flush(created);
  });
});
