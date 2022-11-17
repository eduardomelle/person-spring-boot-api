package br.com.eduardomelle.dto;

import java.io.Serializable;

public class PersonUpdateResponseDTO implements Serializable {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
