package com.example.clertecbankproject.service.impl;

import com.example.clertecbankproject.model.entity.Client;
import com.example.clertecbankproject.model.repository.ClientRepository;
import com.example.clertecbankproject.model.repository.impl.AccountRepositoryImpl;
import com.example.clertecbankproject.model.repository.impl.ClientRepositoryImpl;
import com.example.clertecbankproject.service.ClientService;
import com.example.clertecbankproject.service.impl.util.FakeClient;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


class ClientServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImplTest.class);

    public static final Long ID = 1L;
    private static ClientService clientService;

    private static ClientRepository clientRepository;

    @BeforeAll
    public static void init() {
        clientRepository = mock(ClientRepositoryImpl.class);
        clientService = new ClientServiceImpl(clientRepository);
    }
    @AfterEach
    public void resetMock() {
        reset(clientRepository);
    }

    @BeforeEach
    public void setUp() {
        logger.info("Test for 'ClientServiceImpl' are started.");
    }

    @AfterEach
    public void tearDown() {
        logger.info("Test for 'ClientServiceImpl' are finished.");
        logger.info("*****************************************************************************");
    }

    @Test
    void addClient() throws Exception {
        //given
        Client client = FakeClient.getFirstClient();
        //when
        when(clientRepository.saveClient(client)).thenReturn(true);
        clientService.addClient(client);
        //then
        verify(clientRepository, times(1)).saveClient(client);

    }
    @Test
    public void getAllClients() throws Exception {
        //given
        Client firstClient = FakeClient.getFirstClient();
        Client secondClient = FakeClient.getSecondClient();
        List<Client> clients = new ArrayList<>();
        clients.add(firstClient);
        clients.add(secondClient);
        //when
        when(clientRepository.getAllClients()).thenReturn(clients);
        List<Client> clientsFromDB = clientService.getAllClients();
        //then
        Assertions.assertEquals(clients,clientsFromDB);
        verify(clientRepository, times(1)).getAllClients();
    }
    @Test
    public void getClientById() throws Exception {
        //given
        Client client = FakeClient.getFirstClient();
        //when
        when(clientRepository.getClient(any())).thenReturn(client);
        Client clientFromDB = clientService.getClientById(ID);
        //then
        Assertions.assertEquals(client,clientFromDB);
        verify(clientRepository, times(1)).getClient(any());
    }
    @Test
    public void deleteClient() throws Exception {
        //given
        Client client = FakeClient.getFirstClient();
        when(clientRepository.saveClient(any(Client.class))).thenReturn(true);
        //when
        clientService.addClient(client);
        clientService.deleteClient(ID);
        List<Client> clientsFromDB = clientRepository.getAllClients();
        //then
        Assert.assertEquals(clientsFromDB.size(),0);
    }
}