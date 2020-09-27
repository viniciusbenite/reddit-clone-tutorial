package org.example.redditclone.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.example.redditclone.data_transfer_objects.SubRedditData;
import org.example.redditclone.exceptions.SubredditNotFoundException;
import org.example.redditclone.models.SubReddit;
import org.example.redditclone.repositories.SubRedditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {
    /**
     * AVOID BUSINESS LOGIC IN CONTROLLERS!!
     */

    @Autowired
    SubRedditRepository subRedditRepository;

    @Transactional // relational dB: this garantee consistence
    public SubRedditData save(SubRedditData subRedditData) {
        SubReddit s = subRedditRepository.save(mapSubRedditToDTO(subRedditData));
        subRedditData.setId(s.getSubRedditId());

        return subRedditData;
    }

    private SubReddit mapSubRedditToDTO(SubRedditData subRedditData) {
        /**
         * Lombok builder pattern
         */
        return SubReddit.builder().name(subRedditData.getName()).description(subRedditData.getDescription()).build();
    }

    /**
     * Rerad all/required the subreddits from DB, mapping them to SubredditData 
     * and returning them back to the client. As we just read the data here, 
     * we marked the getAll() and getSubreddit() methods inside the 
     * SubredditService.java as @Transaction(readonly=true)
     * @return a list of all subredits
     */
    @Transactional(readOnly = true)
    public List<SubRedditData> getAll() {
        return subRedditRepository.findAll().stream().map(this::mapToData).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubRedditData getSingleSubReddit(long id) {
        SubReddit sr = subRedditRepository.findById(id)
                        .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return mapToData(sr);
    }

    private SubRedditData mapToData(SubReddit subReddit) {
        return SubRedditData.builder().name(subReddit.getName()).id(subReddit.getSubRedditId())
                .description(subReddit.getDescription()).build();
    }
}
