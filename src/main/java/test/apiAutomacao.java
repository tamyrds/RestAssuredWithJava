package test;

import io.restassured.RestAssured;
import netscape.javascript.JSObject;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.net.URI;

public class apiAutomacao {

    @Test
    public void CadastrarUser(){

        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .body("{\n" +
                        "    \"nome\":\"Tamires Silva\", \"email\":\"teste@te.com.br\",\"password\":\"teste123\",\"administrador\": \"true\"\n" +
                        "        }")
            .when()
                .post("https://serverest.dev/usuarios")
            .then()
                .log().all()
                .statusCode(201)
                .body("message", Matchers.is("Cadastro realizado com sucesso"))
        ;
    }

    @Test
    public void listasUsuarios(){
        RestAssured.given()
                .contentType("application/json")
                .when()
                    .get("https://serverest.dev/usuarios")
                .then()

                .statusCode(200)
                .body("usuarios.nome[0]", Matchers.is("Fulano da Silva"))
        ;
    }
}

