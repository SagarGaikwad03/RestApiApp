package org.ebi.restapiapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.ebi.restapiapp.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestapiappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }
    @Test
    public void testGetAllPerson() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/person",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetPersonById() {
        Person person = restTemplate.getForObject(getRootUrl() + "/employees/1", Person.class);
        System.out.println(person.getFirstName());
        assertNotNull(person);
    }

    @Test
    public void testCreatePersonEntity() {
        Person person = new Person();
        person.setFirstName("dummy_first_name");
        person.setLastName("dummy_last_name");
        person.setAge(50);
        person.setFavouriteColor("Blue");

        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/person", person, Person.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdatePerson() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        person.setFirstName("dummy_first_name1");
        person.setLastName("dummy_last_name1");

        restTemplate.put(getRootUrl() + "/person/" + id, person);

        Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(updatedPerson);
    }

    @Test
    public void testDeletePerson() {
        int id = 2;
        Person person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        assertNotNull(person);

        restTemplate.delete(getRootUrl() + "/person/" + id);

        try {
            person = restTemplate.getForObject(getRootUrl() + "/person/" + id, Person.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
