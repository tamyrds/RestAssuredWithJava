package test;

import io.restassured.RestAssured;
import netscape.javascript.JSObject;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;

import static io.restassured.RestAssured.given;

public class apiAutomacao {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI= "https://serverest.dev";
    }
    @Test
    public void CadastrarUser() {

        given()
                .log().all()
                .contentType("application/json")
                .body("{\n" +
                        "    \"nome\":\"Tamires Silva\", \"email\":\"teste@te1234.com.br\",\"password\":\"teste123\",\"administrador\": \"true\"\n" +
                        "        }")
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(201)
                .body("message", Matchers.is("Cadastro realizado com sucesso"))
        ;
    }

    @Test
    public void listasUsuarios() {
        given()
                .contentType("application/json")
                .when()
                .get("/usuarios")
                .then()

                .statusCode(200)
                .body("usuarios.nome[0]", Matchers.is("Sarah Green III"))
        ;
    }

    @Test
    public void realizarLoginSucesso() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"email\": \"beltrano@qa.com.br\", \"password\": \"teste\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .log().all()
                .body("message", Matchers.is("Login realizado com sucesso"))
        ;
    }

    @Test
    public void realizarLoginInvalido() {
        given()
                .contentType("application/json")
                .body("{\"email\": \"beltran@qa.com.br\", \"password\": \"teste\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(401)
                .log().all()
                .body("message", Matchers.is("Email e/ou senha inválidos"))
        ;
    }
    @Test
    public void buscarUsuariosPeloId(){
        given()
                .when()
                .get("/usuarios/FQsZWahMJNqxDntd")
                .then()
                .statusCode(200)
                .log().all()
                .body("email", Matchers.is("alida.schoen@hotmail.com"))
                ;
    }
    @Test
    public void buscarUsuarioInvalidoPeloId(){
        given()
                .when()
                .get("/usuarios/123455")
                .then()
                .statusCode(400)
                .log().all()
                .body("message", Matchers.is("Usuário não encontrado"));

    }
    @Test
    public void deveRemoverUsuario(){
        given()
                .when()
                .delete("/usuarios/1yUinoOT5p4TFJiu")
                .then()
                .statusCode(200)
                .log().all()
                .body("message", Matchers.is("Registro excluído com sucesso"))
                ;
    }
    @Test
    public void deveRemoverUsuarioJaRemovido(){
        given()
                .when()
                .delete("/usuarios/1yUinoOT5p4TFJiu")
                .then()
                .statusCode(200)
                .log().all()
                .body("message", Matchers.is("Nenhum registro excluído"))
        ;
    }
}