<?php

namespace App\Http\Controllers;
use App\berita_m;
use Illuminate\Http\Request;

class BeritaController extends Controller
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
            $beritas->foto = $foto;
        }
        $beritas->save();

        return redirect()->route('ber.index');
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
        $beritas=berita_m::findOrFail($id);
        return view('edit',['beritas'=>$beritas]);
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
        $beritas = berita_m::findOrFail($id);
        $beritas->nama=$request->nama;
        $beritas->isi=$request->isi;
        $beritas->isii=$request->isii;
       
        if($request->file('foto')){
            if($beritas->foto and file_exists(storage_path('app/public/'.$beritas->foto))){
                \Storage::delete('public/'.$beritas->foto);
            }
            $foto=$request->file('foto')->store('foto_user','public');
            $beritas->foto=$foto;
        }
        $beritas->save();
        return redirect()->route('ber.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $beritas=berita_m::findOrFail($id);
        $beritas->delete();

        return redirect()->route('ber.index');
    }
}
