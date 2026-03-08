package kz.zzhalelov.staffflow.server.hiring;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiringServiceImpl implements HiringService {
    @Override
    public Hiring create() {
        return null;
    }

    @Override
    public List<Hiring> findAll() {
        return List.of();
    }

    @Override
    public Hiring findById(long id) {
        return null;
    }

    @Override
    public Hiring update(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}