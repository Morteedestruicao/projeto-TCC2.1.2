function desabilitaA(){
    $('a').click(function(event){
        if(!$(this).hasClass('dropdown-toggle')){
            event.preventDefault();
            if(!$(this).hasClass('btn')){
                $('a').removeClass('active disabled');
                $(this).addClass('active disabled');
            }
            controleRotasGet($(this).attr("href"));
        }
    });
}

desabilitaA();

$('.navbar-brand').off('click');

function gerarSwal(urlSucesso){
    const swalWithBootstrapButtons = swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success me-2',
            cancelButton: 'btn btn-danger ms-2'
        },
        buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
        title: 'Sair?',
        text: "Você realmente deseja sair da aplicação?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '<i class="fa-solid fa-thumbs-up"></i> Sim!',
        cancelButtonText: '<i class="fa-solid fa-thumbs-down"></i> Não!',
        reverseButtons: false
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href=urlSucesso;
        }
    });
}

function gerarSwal2(urlSucesso,id){
    const swalWithBootstrapButtons = swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success me-2',
            cancelButton: 'btn btn-danger ms-2'
        },
        buttonsStyling: false
    })


    swalWithBootstrapButtons.fire({
        title: 'Deletar?',
        text: "Você realmente deseja deletar o horario ?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '<i class="fa-solid fa-thumbs-up"></i> Sim!',
        cancelButtonText: '<i class="fa-solid fa-thumbs-down"></i> Não!',
        reverseButtons: false
    }).then((result) => {
        if (result.isConfirmed) {
            deletarHorario(id);
        }
    });
}

function gerarSwal3(urlSucesso,id){
    const swalWithBootstrapButtons = swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success me-2',
            cancelButton: 'btn btn-danger ms-2'
        },
        buttonsStyling: false
    })


    swalWithBootstrapButtons.fire({
        title: 'Deletar?',
        text: "Você realmente deseja deletar o horario ?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '<i class="fa-solid fa-thumbs-up"></i> Sim!',
        cancelButtonText: '<i class="fa-solid fa-thumbs-down"></i> Não!',
        reverseButtons: false
    }).then((result) => {
        if (result.isConfirmed) {
            deletarHorario2(id);
        }
    });
}

function addReserva(){
    let horarioE = $("#horarioE").val();
    let horarioS = $("#horarioS").val();
    let sala = $("#sala").val();

    $.ajax({
           type: "POST",
           url: "/Reserva",
           data: {
               horarioE: horarioE,
               horarioS: horarioS,
               sala: sala
           },
            success: function (data){
            if(data.sucesso){
               mensagemSucesso(data.mensagem);
               $('#novaReserva').modal('hide');
               criarLinha(data.id
               , horarioE, horarioS, sala)
               }
               else{
               mensagemErro(data.mensagem)
               $('#novaReserva').modal('hide');
               }

            },
            error: function (){
               mensagemErro("Houve um problema ao salvar o Horario.");
            }

        });

}

$(document).ready(function() {
    function updateStatus() {
        $.ajax({
            url: '/arduinoStatus', // Endpoint do Spring Boot que retorna os status do Arduino
            type: 'GET',
            success: function(data) {
            if(data.sucesso){
                $('#buttonStatus').text(data.buttonStatus ? 'O botão de emergência foi pressionado!' : '');
                $('#doorStatus').text(data.doorStatus ? 'A porta está obstruída.' : '');
            }
            else{
            mensagemErro(data.mensagem)
            },
            }
            error: function() {
                mensagemErro("Houve um problema ao salvar o Horario.");/
            }
        });
    }

    setInterval(updateStatus, 2000); // Atualiza a cada 2 segundos (ajuste conforme necessário)
});

function addReservaSema(){
    let horarioESema = $("#horarioESema").val();
    let horarioSSema = $("#horarioSSema").val();
    let date_ini = $("#date_ini").val();
    let date_fim = $("#date_fim").val();
    let salaSema = $("#salaSema").val();
    let segunda = $("#segunda").prop('checked');
    let terca = $("#terca").prop('checked');
    let quarta = $("#quarta").prop('checked');
    let quinta = $("#quinta").prop('checked');
    let sexta = $("#sexta").prop('checked');
    let sabado = $("#sabado").prop('checked');
    let domingo = $("#domingo").prop('checked');


    $.ajax({
           type: "POST",
           url: "/ReservaSema",
           data: {
               horarioESema: horarioESema,
               horarioSSema: horarioSSema,
               date_ini : date_ini,
               date_fim : date_fim,
               salaSema: salaSema,
               segunda : segunda,
               terca : terca,
               quarta : quarta,
               quinta : quinta,
               sexta : sexta,
               sabado : sabado,
               domingo : domingo
           },
            success: function (data){
            if(data.sucesso){
               mensagemSucesso(data.mensagem);
               $('#novaReservaSema').modal('hide');
//               criarLinhaSema(data.id, horarioESema, horarioSSema, salaSema, segunda, terca, quarta, quinta, sexta, sabado, domingo)
               }
               else{
               mensagemErro(data.mensagem)
               $('#novaReservaSema').modal('hide');
               }

            },
            error: function (){
               mensagemErro("Houve um problema ao salvar o Horario.");
            }

        });

}

function mensagemSucesso(mensagem){
Swal.fire({
   position: 'top-end',
   icon: 'success',
   title: mensagem,
   showConfirmButton: false,
   timer: 2000
 })
 }

 function mensagemErro(mensagem){
 Swal.fire({
    position: 'top-end',
    icon: 'error',
    title: mensagem,
    showConfirmButton: false,
    timer: 2000
  })
  }

function deletarHorario(idHorario){
     $.ajax({
               type: "POST",
               url: "/deletar",
               data: {
                   idHorario: idHorario,
               },
                success: function (data){
                mensagemSucesso("Deletado com sucesso.");
                $("#excloi"+idHorario).remove();
                },
                error: function (){
                   mensagemErro("Não foi possivel deletar o horario.")
                }
            });
}

function deletarHorario2(idHorario){
     $.ajax({
               type: "POST",
               url: "/deletarSema",
               data: {
                   idHorario: idHorario,
               },
                success: function (data){
                mensagemSucesso("Deletado com sucesso.");
                $("#excloiSema"+idHorario).remove();
                },
                error: function (){
                   mensagemErro("Não foi possivel deletar o horario.")
                }
            });
}



function criarLinha(idHorario, horarioE, horarioS, sala){
    let data_aber = new Date(horarioE);
    let data_fech = new Date(horarioS);
    $("#listaReservas").append('<tr>' +
    '<td>' + idHorario + '</td>' +
    '<td>' + data_aber.toLocaleDateString() + ' '+ data_aber.toLocaleTimeString() + '</td>' +
    '<td>' + data_fech.toLocaleDateString() + ' '+ data_fech.toLocaleTimeString() + '</td>' +
    '<td>' + sala + '</td>' +
    '<td> <a th:href="${/removerR/ + reserva.id}" class="btn btn-sm btn-danger">X</a></td> '+
    '</tr>');
}

function criarLinhaSema(idHorario, horarioE, horarioS, sala, segunda, terca, quarta, quinta, sexta, sabado, domingo){
    $("#listaReservas").append('<tr>' +
    '<td>' + + '<td>' +
    '<td>' + idHorario + '</td>' +
    '<td>' + +
    '<td>' + +
    '<td>' + sala + '</td>' +
    '<td> <a th:href="${/removerR/ + reserva.id}" class="btn btn-sm btn-danger">X</a></td> '+
    '</tr>');
}

function enviarEmail(){
    let email = $("#emailEnviar").val();

    $.ajax({
         type: "POST",
         url: "/Relatorio",
         data: {
           email: email,
         },
          success: function (data){
          mensagemSucesso("E-Mail enviado com sucesso.");
          },
          error: function (){
          mensagemErro("Não foi possível enviar o E-Mail.")
                    }
                });
}
