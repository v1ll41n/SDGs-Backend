package com.techdev.sdg.Admin;

import com.techdev.sdg.Entity.Entity;
import com.techdev.sdg.Entity.UserEntity;
import com.techdev.sdg.NGO.NGO;
import com.techdev.sdg.NGO.NGORepository;
import com.techdev.sdg.PrivateSector.PrivateSector;
import com.techdev.sdg.PrivateSector.PrivateSectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {
    @Autowired
    private PrivateSectorRepository privateSectorRepository;

    @Autowired
    private NGORepository ngoRepository;

    public List<Map<String, Object>> getSignupRequests() {
        List<PrivateSector> privateSectors = privateSectorRepository.findByIsApproved(false);
        List<NGO> ngos = ngoRepository.findByIsApproved(false);

        List<Entity> e = Stream.concat(privateSectors.stream(), ngos.stream())
                .collect(Collectors.toList());

        List<Map<String, Object>> entities = new ArrayList<>();

        for (Entity entity : e)
            entities.add(entity.toMap());
        return entities;
    }

    public void approveSignupRequest(Long id, String type) throws Exception {
        if (type.equals("PrivateSector")) {
            PrivateSector entity = privateSectorRepository.findById(id).get();
            entity.setIsApproved(true);
            privateSectorRepository.save(entity);
        }
        else if (type.equals("NGO")) {
            NGO entity = ngoRepository.findById(id).get();
            entity.setIsApproved(true);
            ngoRepository.save(entity);
        } else
            throw new Exception("Invalid entity type passed");
    }
}
