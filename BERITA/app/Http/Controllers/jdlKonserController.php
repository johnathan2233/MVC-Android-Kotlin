<?php

namespace App\Http\Controllers;
use App\berita_m;
use Illuminate\Http\Request;

class jdlKonserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $beritas = berita_m::all();
        return view('beranda',['beritas'=>$beritas]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('add');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $beritas = new \App\berita_m;
        $beritas->nama=$request->nama;
        $beritas->isi=$request->isi;
        $beritas->isii=$request->isii;
        if($request->file('foto')){
            $foto = $request->file('foto')->store('user_foto','public');
            $konsers->foto = $foto;
        }
        $konsers->save();

        return redirect()->route('user.index');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $konsers=konser_m::findOrFail($id);
        return view('edit',['konsers'=>$konsers]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $konsers = konser_m::findOrFail($id);
        $konsers->nama=$request->nama;
        $konsers->tempat=$request->tempat;
        $konsers->tanggal=$request->tanggal;
        $konsers->jam=$request->jam;
       

        if($request->file('foto')){
            if($konsers->foto and file_exists(storage_path('app/public/'.$konsers->foto))){
                \Storage::delete('public/'.$konsers->foto);
            }
            $foto=$request->file('foto')->store('foto_user','public');
            $konsers->foto=$foto;
        }
        $konsers->save();
        return redirect()->route('user.index');
    }
    

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $konsers=konser_m::findOrFail($id);
        $konsers->delete();

        return redirect()->route('user.index');
    }
}
