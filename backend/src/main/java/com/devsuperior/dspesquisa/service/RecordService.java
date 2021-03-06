package com.devsuperior.dspesquisa.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.entities.Game;
import com.devsuperior.dspesquisa.entities.Record;
import com.devsuperior.dspesquisa.repositories.GameRepository;
import com.devsuperior.dspesquisa.repositories.RecordRepository;

@Service
public class RecordService {

	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Transactional(readOnly = true)
	public RecordDTO saveRecord(RecordInsertDTO recordInsertDTO){
		Record record = new Record();
		
		record.setName(recordInsertDTO.getName());
		record.setAge(recordInsertDTO.getAge());
		record.setMoment(Instant.now());
		
		Game game = new Game();
		game = gameRepository.getOne(recordInsertDTO.getGameId());
		record.setGame(game);
		
		record = recordRepository.save(record);
		
		return new RecordDTO(record);
		
	}
	
	@Transactional(readOnly = true)
	public Page<RecordDTO> findByMoments(Instant minDate, Instant maxDate, PageRequest pageRequest) {
		return recordRepository.findByMoments(minDate, maxDate, pageRequest).map(record -> new RecordDTO(record));
	}
}
