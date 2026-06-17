@Override
public Project createProject(ProjectDTO dto) {

    User owner = userRepository.findById(dto.getOwnerId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Project project = new Project();
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setStartDate(dto.getStartDate());
    project.setOwner(owner);

    return projectRepository.save(project);
}