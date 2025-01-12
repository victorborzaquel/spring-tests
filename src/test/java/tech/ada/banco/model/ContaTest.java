package tech.ada.banco.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.ada.banco.exceptions.SaldoInsuficienteException;
import tech.ada.banco.exceptions.ValorInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tech.ada.banco.utils.Format.format;

class ContaTest {
    private final Pessoa pessoa = new Pessoa("João", "123.456.789-00", LocalDate.of(1990, 1, 1));
    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta(ModalidadeConta.CC, pessoa);
    }

    @Test
    void testDeposito() {
        conta.deposito(BigDecimal.TEN);

        assertEquals(format(10), conta.getSaldo());
    }

    @Test
    void testDepositoValorMenorQueZero() {
        final BigDecimal valor = BigDecimal.valueOf(-1);

        assertThrows(ValorInvalidoException.class, () -> conta.deposito(valor));
        assertEquals(format(0), conta.getSaldo());
    }

    @Test
    void testSaque() {
        conta.deposito(BigDecimal.valueOf(7));

        conta.saque(BigDecimal.valueOf(3));

        assertEquals(format(4), conta.getSaldo());
    }

    @Test
    void testSaqueValorMenorQueZero() {
        final BigDecimal valor = BigDecimal.valueOf(-1);
        conta.deposito(BigDecimal.valueOf(7));

        assertThrows(ValorInvalidoException.class, () -> conta.saque(valor));
        assertEquals(format(7), conta.getSaldo());
    }

    @Test
    void testSaqueSaldoInsuficiente() {
        final BigDecimal valor = BigDecimal.valueOf(3);

        assertThrows(SaldoInsuficienteException.class, () -> conta.saque(valor));
        assertEquals(format(0), conta.getSaldo());
    }
}