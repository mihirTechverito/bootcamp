package com.techverito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TserviceTest {

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Mock Trepository trepositoryMock;

  @Test
  void taxApplied() {

    assertNotNull(trepositoryMock);
    when(trepositoryMock.taxRate()).thenReturn(5);
    Tservice tservice = new Tservice(trepositoryMock);
    assertEquals(25, tservice.taxApplied());
    verify(trepositoryMock, times(1)).taxRate();
  }
}
