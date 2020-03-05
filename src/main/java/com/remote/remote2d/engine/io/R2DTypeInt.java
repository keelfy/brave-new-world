package com.remote.remote2d.engine.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import nu.xom.Element;

public class R2DTypeInt extends R2DType {
	
	public int data;

	public R2DTypeInt(String id) {
		super(id);
	}
	
	public R2DTypeInt(String id, int data) {
		this(id);
		this.data = data;
	}

	@Override
	public void read(DataInput d) throws IOException {
		data = d.readInt();
	}

	@Override
	public void write(DataOutput d) throws IOException {
		d.writeInt(data);
	}
	
	@Override
	public void read(Element e) {
		data = Integer.parseInt(e.getValue());
	}

	@Override
	public void write(Element e) {
		e.appendChild(Integer.toString(data));
	}

	@Override
	public byte getId() {
		return 3;
	}
	
	@Override
	public String toString()
	{
		String hexString = Integer.toHexString(data);
		return data+" (0x"+hexString+")";
	}
	
	@Override
	public boolean equals(R2DType type)
	{
		if(!type.name.equals(name) || !(type instanceof R2DTypeInt))
			return false;
		return ((R2DTypeInt)type).data == data;
	}
	
	@Override
	public R2DTypeInt clone()
	{
		return new R2DTypeInt(name,data);
	}

}
