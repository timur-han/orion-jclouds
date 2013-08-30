/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.orion.domain.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.orion.domain.OrionStorageMetadata;

/**
 * PageSet implementation of Orion
 * 
 * @author timur
 * 
 */
public class OrionPageSet implements PageSet<OrionStorageMetadata> {
	List<OrionStorageMetadata> orionStorageMetadataList;

	/**
	 * @param storageDataList
	 */
	public OrionPageSet(List<OrionStorageMetadata> orionStorageMetadataList) {
		this.orionStorageMetadataList = orionStorageMetadataList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return orionStorageMetadataList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return orionStorageMetadataList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return orionStorageMetadataList.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<OrionStorageMetadata> iterator() {
		return orionStorageMetadataList.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		return orionStorageMetadataList.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return orionStorageMetadataList.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(OrionStorageMetadata e) {

		return orionStorageMetadataList.add(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return orionStorageMetadataList.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return orionStorageMetadataList.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends OrionStorageMetadata> c) {
		return orionStorageMetadataList.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return orionStorageMetadataList.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return orionStorageMetadataList.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		orionStorageMetadataList.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jclouds.blobstore.domain.PageSet#getNextMarker()
	 */
	@Override
	public String getNextMarker() {
		return null;
	}

}
