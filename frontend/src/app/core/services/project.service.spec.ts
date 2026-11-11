import { TestBed } from '@angular/core/testing';
import { ProjectService } from './project.service';
import { ApiService } from './api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('ProjectService', () => {
  let service: ProjectService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProjectService, ApiService]
    });
    service = TestBed.inject(ProjectService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getAll should return projects', () => {
    const mockProjects = [
      { id: 1, name: 'Projet A', description: 'Desc', startDate: '2024-01-01', ownerId: 1 }
    ];
    service.getAll().subscribe((projects) => {
      expect(projects.length).toBe(1);
      expect(projects[0].name).toBe('Projet A');
    });
    const req = httpMock.expectOne('/projects');
    expect(req.request.method).toBe('GET');
    req.flush(mockProjects);
  });

  it('create should post a project', () => {
    const newProject = { name: 'Projet B', description: 'Desc', startDate: '2024-02-01', ownerId: 2 };
    const created = { id: 2, ...newProject };
    service.create(newProject).subscribe((project) => {
      expect(project.id).toBe(2);
    });
    const req = httpMock.expectOne('/projects');
    expect(req.request.method).toBe('POST');
    expect(req.request.body.name).toBe('Projet B');
    req.flush(created);
  });
});
