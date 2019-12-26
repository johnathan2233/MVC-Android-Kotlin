<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <script src='https://kit.fontawesome.com/a076d05399.js'></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <title>List</title>
</head>
<body>
    
        <br>
        <a href="{{route('ber.create')}}" class="btn btn-success">tambah Jadwal</a>
        <br>
        <br>

<table class="table table-bordered">
        <thead class="bg-info">
          <tr>
            
            <th scope="col">Judul</th>
            {{-- <th scope="col">Link Berita</th> --}}
            <th scope="col">Kategori</th>
            <th scope="col">Foto</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            @foreach ($beritas as $ber)
                
            
          <td>{{$ber->nama}}</td>
            {{-- <td>{{$ber->isi}}</td> --}}
            <td>{{$ber->isii}}</td>
            <td><img src="{{asset('storage/'.$ber->foto)}}" height="100px" width="100px" alt=""></td>
            <td>
                    <a href="{{route('ber.edit',['id'=>$ber->id])}}" class="btn btn-flat btn-xs btn-warning" style="color:aliceblue">Edit</a>
                    <br>
                    <form action="{{route('ber.destroy',['id'=>$ber->id])}}" method="POST">
                      @method('delete')
                      @csrf
                      <br>
                      <input type="submit" class="btn btn-flat btn-xs btn-danger btn-hapus" value="delete">
                    </form>

            </td>
          </tr>
          @endforeach
        </tbody>
      </table>
    
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>