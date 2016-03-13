/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.domain.vo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author changshu.li
 */
public class Paging<T> {

	public static final int DEFAULT_SIZE = 50;
	public static final int DEFAULT_PAGE = 1;
	private int page;//页号 从 1 开始
	private int size;//每页 行数
	private final List<Sort.Order> sort = new ArrayList<Sort.Order>();
	protected transient Page<T> result = null;

	public Paging(Integer page) {
		this(page, null);
	}

	public Paging() {
		this(null, null);
	}

	public Paging(String... sort) {
		this(null, null, sort);
	}

	public Paging(Integer page, Integer size) {
		this(page, size, DEFAULT_PAGE, DEFAULT_SIZE);
	}

	public Paging(Integer page, Integer size, int defpage, int defsize) {
		this.page = (page == null || page < 1 ? defpage : page);
		this.size = (size == null || size < 1 ? defsize : size);
	}

	public Paging(Integer page, Integer size, String... sort) {
		this(page, size);
		for (String str : sort) {
			this.addSort(str);
		}
	}

	public int getPage() {
		return page;
	}

	public Paging<T> setPage(int page) {
		this.page = page;
		return this;
	}

	public Paging<T> setOffset(int offset) {
		this.page = offset / page;
		return this;
	}

	public int getSize() {
		return size;
	}

	public Paging<T> setSize(int size) {
		this.size = size;
		return this;
	}

	public Paging<T> addSort(String sort) {
		sort = sort.trim();
		String pp = sort;
		Sort.Direction st = Sort.Direction.ASC;
		if (sort.length() == 0) {
			throw new IllegalArgumentException("sort is empty!");
		}
		int idx = sort.indexOf(' ');
		if (idx > 0) {
			pp = sort.substring(0, idx);
			if (sort.substring(idx).trim().equalsIgnoreCase("DESC")) {
				st = Sort.Direction.DESC;
			}
		}
		this.sort.add(new Sort.Order(st, pp));
		return this;
	}

	public Pageable toPage() {
		Sort.Order[] ord = new Sort.Order[this.sort.size()];
		Sort st = null;
		if (ord.length > 0) {
			st = new Sort(this.sort.toArray(ord));
		}
		return new PageRequest(this.page - 1, this.size, st);
	}

	public Page<T> getResult() {
		if (result == null) {
			result = new PageImpl<T>(new ArrayList<T>());
		}
		return result;
	}

	public Paging<T> setResult(Page<T> result) {
		this.result = result;
		return this;
	}

	public static Paging parsePage(int[] page, String... sort) {
		if (page == null || page.length == 0) {
			page = new int[]{0, DEFAULT_SIZE};
		}
		if (page.length == 1) {
			page = new int[]{page[0], DEFAULT_SIZE};
		}
		if (page.length == 2) {
			page = new int[]{page[0], page[1]};
		}
		if (page.length > 2) {
			throw new IllegalArgumentException("Page max lenth is 2, eg: pageNo and size!");
		}
		Paging pg = new Paging(page[0], page[1]);
		if (sort != null) {
			for (String str : sort) {
				pg.addSort(str);
			}
		}
		return pg;
	}

	public StringBuffer getOrderString(String spli) {
		if (spli == null) {
			spli = ",";
		}
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < this.sort.size(); i++) {
			Sort.Order s = this.sort.get(i);
			if (Sort.Direction.ASC == s.getDirection()) {
				buff.append(s.getProperty()).append(" asc");
			} else {
				buff.append(s.getProperty()).append(" desc");
			}
			buff.append(spli);
		}
		if (buff.length() > 0) {
			buff.setLength(buff.length() - spli.length());
		}
		return buff;
	}

	public List<T> getContent() {
		return this.getResult().getContent();
	}

	public long getTotalElements() {
		return this.getResult().getTotalElements();
	}

	public int getFrom() {
		return (this.getPage() - 1) * this.getSize();
	}
}
