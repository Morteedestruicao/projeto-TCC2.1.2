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
               mensagemSucesso("Horario Salvo com Sucesso.");
               $('#novaReserva').modal('hide');
               criarLinha(data.id, horarioE, horarioS, sala)

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
   timer: 1500
 })
 }

 function mensagemErro(mensagem){
 Swal.fire({
    position: 'top-end',
    icon: 'error',
    title: mensagem,
    showConfirmButton: false,
    timer: 1500
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
    $("#listaReservas").append('<tr>' +
    '<td>' + idHorario + '</td>' +
    '<td>' + new Date(horarioE).toLocaleDateTimeString() + '</td>' +
    '<td>' + new Date(horarioS).toLocaleDateTimeString() + '</td>' +
    '<td>' + sala + '</td>' +
    '<td> <a th:href="${/removerR/ + reserva.id}" class="btn btn-sm btn-danger">-</a></td> '+
    '</tr>');
}