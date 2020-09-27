package org.example.redditclone.services;

import java.util.List;
import java.util.stream.Collectors;

import org.example.redditclone.data_transfer_objects.SubRedditData;
import org.example.redditclone.exceptions.SubredditNotFoundException;
import org.example.redditclone.mappers.SubRedditMapperInterface;
import org.example.redditclone.models.SubReddit;
import org.example.redditclone.repositories.SubRedditRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubRedditService {
    /**
     * AVOID BUSINESS LOGIC IN CONTROLLERS!!
     */

    @Autowired
    private SubRedditRepository subRedditRepository;
    @Autowired
    private SubRedditMapperInterface subRedditMapperInterface;

    @Transactional // relational dB: this garantee consistence
    public SubRedditData save(SubRedditData subRedditData) {
        SubReddit s = subRedditRepository.save(subRedditMapperInterface.mapDataToSubReddit(subRedditData));
        subRedditData.setId(s.getSubRedditId());

        return subRedditData;
    }

    /**
     * Read all/required the subreddits from DB, mapping them to SubredditData 
     * and returning them back to the client. As we just read the data here, 
     * we marked the getAll() and getSubreddit() methods inside the 
     * SubredditService.java as @Transaction(readonly=true)
     * @return a list of all subredits
     */
    @Transactional(readOnly = true)
    public List<SubRedditData> getAll() {
        return subRedditRepository.findAll()
        .stream()
        .map(subRedditMapperInterface::mapSubRedditToData)
        .collect(Collectors.toList());
    }

    /**
     * 
     * @param Long id
     * @return Sub reddit DTO
     */
    @Transactional(readOnly = true)
    public SubRedditData getSingleSubReddit(long id) {
        SubReddit sr = subRedditRepository.findById(id)
                        .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return subRedditMapperInterface.mapSubRedditToData(sr);
    }
}
