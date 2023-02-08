package com.mongodb;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UniCardMongoBeanRepository extends MongoRepository<UniCardMongoBean, String>{

    public List<UniCardMongoBean> findAll();
    public Page<UniCardMongoBean> findAll(Pageable pageable);

    @SuppressWarnings("unchecked")
    public UniCardMongoBean save(UniCardMongoBean entity);
	
    public Optional<UniCardMongoBean> findById(String id);
    public List<UniCardMongoBean> findByCustomerId(String customerId);
    public void deleteById(String id);

	@Query("{'usedDate' : { $gte: ?0, $lte: ?1 } }")                 
	public Page<UniCardMongoBean> findAllByDate(Date from, Date to, Pageable pageable);
	@Query("{'customerId' : ?0, 'usedDate': ?1 } ")                 
	public UniCardMongoBean findByCustomerIdDate(String customerId, Date date);
}
