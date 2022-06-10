//
//    function readURL(input){
//        if(input.files && input.files[0]){
//            var reader = new FileReader();
//            reader.onload = function(e){
//                $('#imgPreview').attr('src', e.target.result).width(100).height(100);
//            }
//            reader.readAsDataURL(input.files[0])
//        }
//    }
//    $('#productImage').change(function(){
//        readURL(this);
//    });
//    $(".custom-file-input").on("change", function() {
//        var fileName = $(this).val().split("\\").pop();
//        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
//    });
