package project.example.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryExample extends JpaRepository<DomainExample, Long> {
    
}
